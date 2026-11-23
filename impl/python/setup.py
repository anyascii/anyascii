#!/usr/bin/env python
# Keep the pure Python build available to tools that predate PEP 517.

from distutils.core import setup

from anyascii import __version__

setup(
    name="anyascii-rs",
    version=__version__,
    description="Unicode to ASCII transliteration",
    author="Hunter WB",
    author_email="hunter@hunterwb.com",
    maintainer="PLPeeters",
    maintainer_email="PLPeeters@users.noreply.github.com",
    url="https://github.com/PLPeeters/anyascii",
    python_requires=">=3.10",
    packages=["anyascii", "anyascii._data"],
    package_data={"anyascii": ["py.typed"], "anyascii._data": ["*"]},
)
