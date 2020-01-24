import os
import setuptools


def read(fname):
    return open(os.path.join(os.path.dirname(__file__), fname), encoding='utf-8').read()


setuptools.setup(
    author="Hunter WB",
    author_email="hunter@hunterwb.com",
    name='anyascii',
    version='0.1.0',
    description='Unicode to ASCII transliteration',
    long_description=read('../README.md'),
    license='ISC',
    url='https://github.com/hunterwb/any-ascii',
    packages=setuptools.find_packages(),
    python_requires=">=3.3",
    zip_safe=True,
    classifiers=[
        'License :: OSI Approved :: ISC License (ISCL)',
        'Topic :: Text Processing',
        'Programming Language :: Python :: 3.8',
        'Programming Language :: Python :: 3.7',
        'Programming Language :: Python :: 3.6',
        'Programming Language :: Python :: 3.5',
        'Programming Language :: Python :: 3.4',
        'Programming Language :: Python :: 3',
        'Programming Language :: Python :: 3 :: Only',
    ]
)