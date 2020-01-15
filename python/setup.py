import setuptools

setuptools.setup(
    author="Hunter WB",
    author_email="hunter@hunterwb.com",
    name='anyascii',
    version='0.1.0-dev',
    description='Unicode to ASCII transliteration',
    url='https://github.com/hunterwb/any-ascii',
    packages=setuptools.find_packages(),
    python_requires=">=3.3",
    zip_safe=True,
    classifiers=[
        'License :: OSI Approved :: ISC License (ISCL)'
    ]
)