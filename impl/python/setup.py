#!/usr/bin/env python
# setup.py generated by flit for tools that don't yet use PEP 517

from distutils.core import setup

packages = ["anyascii", "anyascii._data"]

package_data = {"": ["*"]}

setup(
    name="anyascii",
    version="0.3.3",
    description="Unicode to ASCII transliteration",
    author="Hunter WB",
    author_email="hunter@hunterwb.com",
    url="https://github.com/anyascii/anyascii",
    packages=packages,
    package_data=package_data,
    python_requires=">=3.3",
)
