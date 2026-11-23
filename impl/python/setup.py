#!/usr/bin/env python
# Keep the pure Python build available to tools that predate PEP 517.

from distutils.core import setup

from anyascii import __version__

setup(
    name="anyascii",
    version=__version__,
    description="Unicode to ASCII transliteration",
    author="Hunter WB",
    author_email="hunter@hunterwb.com",
    url="https://github.com/anyascii/anyascii",
    python_requires=">=3.3",
    packages=["anyascii", "anyascii._data"],
    package_data={"anyascii": ["py.typed"], "anyascii._data": ["*"]},
)
