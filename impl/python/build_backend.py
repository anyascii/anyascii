"""Build pure Python distributions with Flit."""

from pathlib import Path
from tempfile import TemporaryDirectory
from zipfile import ZipFile

from flit_core import buildapi as flit

build_sdist = flit.build_sdist
get_requires_for_build_sdist = flit.get_requires_for_build_sdist
get_requires_for_build_wheel = flit.get_requires_for_build_wheel
prepare_metadata_for_build_wheel = flit.prepare_metadata_for_build_wheel

if hasattr(flit, "build_editable"):
    build_editable = flit.build_editable
    get_requires_for_build_editable = flit.get_requires_for_build_editable
    prepare_metadata_for_build_editable = flit.prepare_metadata_for_build_editable


def build_wheel(wheel_directory, config_settings=None, metadata_directory=None):
    # Flit applies its file exclusions when building the sdist.
    with TemporaryDirectory(dir=wheel_directory) as build_directory:
        name = flit.build_wheel(build_directory, config_settings, metadata_directory)
        artifact = Path(build_directory, name)
        with ZipFile(artifact) as wheel:
            if any(
                path.startswith("anyascii/_transliterate.") for path in wheel.namelist()
            ):
                raise RuntimeError(
                    "The source package contains a native extension. "
                    "Run `python -m build` to build the universal wheel from the sdist."
                )
        artifact.replace(Path(wheel_directory, name))
    return name
