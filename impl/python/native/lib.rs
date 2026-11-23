use any_ascii::try_any_ascii;
use pyo3::exceptions::{PyImportError, PyMemoryError, PyUnicodeEncodeError};
use pyo3::ffi;
use pyo3::prelude::*;
use pyo3::types::{PyBytes, PyString};

#[pyfunction(signature = (string, /))]
fn transliterate<'py>(string: &Bound<'py, PyAny>) -> PyResult<Bound<'py, PyAny>> {
    let py = string.py();
    let Ok(text) = string.cast_exact::<PyString>() else {
        return py
            .import("anyascii._pure")?
            .call_method1("transliterate", (string,));
    };

    let encoded;
    let input = match text.to_str() {
        Ok(input) => input,
        Err(error) if error.is_instance_of::<PyUnicodeEncodeError>(py) => {
            // Lone surrogates have no transliteration.
            encoded = text
                .call_method1("encode", ("utf-8", "ignore"))?
                .cast_into::<PyBytes>()?;
            std::str::from_utf8(encoded.as_bytes()).expect("str.encode returned invalid UTF-8")
        }
        Err(error) => return Err(error),
    };
    let output = try_any_ascii(input).map_err(|_| PyMemoryError::new_err(()))?;
    PyString::from_bytes(py, output.as_bytes()).map(Bound::into_any)
}

#[pymodule(gil_used = false)]
fn _transliterate(module: &Bound<'_, PyModule>) -> PyResult<()> {
    // CPython creates the main interpreter first and assigns it ID 0. Some
    // subinterpreters can load this extension, but PyO3 cannot safely use them.
    // PyO3 calls this with the thread attached, so PyInterpreterState_Get is valid.
    let id = unsafe { ffi::PyInterpreterState_GetID(ffi::PyInterpreterState_Get()) };
    match id {
        0 => module.add_function(wrap_pyfunction!(transliterate, module)?),
        -1 => Err(PyErr::fetch(module.py())),
        _ => Err(PyImportError::new_err(
            "Native transliteration requires the main interpreter",
        )),
    }
}
