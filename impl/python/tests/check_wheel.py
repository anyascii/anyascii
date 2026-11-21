"""Check the installed wheel in an environment outside the source tree."""

import argparse
import subprocess
import sys
from importlib.machinery import ExtensionFileLoader
from importlib.metadata import distribution
from pathlib import Path
from textwrap import dedent


def check_wheel(backend):
    from anyascii import __version__, _transliterate_text, anyascii

    package = distribution("anyascii-rs")
    assert package.version == __version__
    wheel = package.read_text("WHEEL").splitlines()
    if backend == "native":
        assert any(tag.startswith("Tag: cp310-abi3-") for tag in wheel), wheel
        module = sys.modules[_transliterate_text.__module__]
        assert isinstance(module.__loader__, ExtensionFileLoader)
    else:
        assert "Tag: py3-none-any" in wheel, wheel
        native_files = [
            path for path in package.files if path.suffix in {".so", ".pyd"}
        ]
        assert not native_files, native_files
        assert _transliterate_text.__module__ == "anyascii._pure"

    ascii_text = "ASCII\0text"
    assert anyascii(ascii_text) is ascii_text


def check_unicode(table):
    from anyascii import anyascii

    with table.open(encoding="utf-8") as source:
        mappings = dict(line.rstrip("\n").split("\t") for line in source)
    for codepoint in range(0x110000):
        char = chr(codepoint)
        expected = char if codepoint < 0x80 else mappings.get(char, "")
        assert anyascii(char) == expected, "U+%04X" % codepoint


def check_subinterpreter():
    code = (
        "from anyascii import anyascii, _transliterate_text\n"
        "assert _transliterate_text.__module__ == 'anyascii._pure'\n"
        "assert anyascii('é\\ud800👑') == 'e:crown:'"
    )
    if sys.version_info >= (3, 14):
        from concurrent import interpreters

        interpreter = interpreters.create()
        try:
            interpreter.exec(code)
        finally:
            interpreter.close()
    else:
        try:
            import _xxsubinterpreters as interpreters
        except ImportError:
            return

        interpreter = interpreters.create()
        try:
            interpreters.run_string(interpreter, code)
        finally:
            interpreters.destroy(interpreter)


def check_memory_limit(character):
    code = dedent(
        r"""
        import ctypes
        import os
        import resource
        import sys

        from anyascii import anyascii

        utf8 = ctypes.pythonapi.PyUnicode_AsUTF8AndSize
        utf8.argtypes = [ctypes.py_object, ctypes.c_void_p]
        utf8.restype = ctypes.c_void_p
        character = sys.argv[1]
        text = character * 12_000_000
        utf8(text, None)  # Set the limit after CPython caches the UTF-8 encoding.
        with open("/proc/self/statm") as statm:
            size = int(statm.read().split()[0]) * os.sysconf("SC_PAGE_SIZE")
        _, hard = resource.getrlimit(resource.RLIMIT_AS)
        resource.setrlimit(resource.RLIMIT_CORE, (0, 0))
        resource.setrlimit(resource.RLIMIT_AS, (size + 4 * 1024**2, hard))
        if character == "\ue000":
            assert anyascii(text) == ""
        else:
            try:
                anyascii(text)
            except MemoryError:
                pass
            else:
                raise AssertionError("Expected the output allocation to fail")
        """
    )
    subprocess.run([sys.executable, "-I", "-c", code, character], check=True)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("backend", choices=("pure", "native"))
    parser.add_argument("table", nargs="?", type=Path)
    args = parser.parse_args()
    if args.backend == "native":
        check_subinterpreter()
    check_wheel(args.backend)
    if args.table:
        check_unicode(args.table)
    if args.backend == "native":
        check_subinterpreter()
        if sys.platform == "linux":
            check_memory_limit("\ue000")
            check_memory_limit("é")
