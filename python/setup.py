import setuptools

setuptools.setup(
    author="Hunter WB",
    author_email="hunter@hunterwb.com",
    name='anyascii',
    version='0.1.0-dev',
    description='Unicode to ASCII transliteration',
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
    ]
)