import os
import setuptools


def read(fname):
    p = os.path.join(os.path.dirname(__file__), fname)
    if os.path.exists(p):
        return open(p, encoding='utf-8').read()
    else:
        return ""


setuptools.setup(
    author="Hunter WB",
    author_email="hunter@hunterwb.com",
    name='anyascii',
    version='0.1.7-dev',
    description='Unicode to ASCII transliteration',
    long_description=read('../README.md'),
    long_description_content_type='text/markdown',
    license='ISC',
    url='https://github.com/anyascii/anyascii',
    packages=setuptools.find_packages(),
    package_data={'anyascii._data': ['*']},
    python_requires=">=3.3",
    zip_safe=True,
    classifiers=[
        'License :: OSI Approved :: ISC License (ISCL)',
        'Topic :: Text Processing :: General',
        'Topic :: Text Processing :: Linguistic',
        'Programming Language :: Python :: 3 :: Only',
    ]
)
