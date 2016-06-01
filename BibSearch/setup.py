# -*- coding: utf-8 -*-

"""
    Setup pastebin package
"""

from pip.req import parse_requirements
from setuptools import setup, find_packages

setup(
    name="BibSearch",

    packages=find_packages(),
    include_package_data=True,

    install_requires=list(x.name for x in parse_requirements("./requirements.txt", session=False)),

    console=['main.py'],
)
