#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Mariusz Kozakowski 2016

"""Main program"""

from ScienceDirect import ScienceDirect

sd = ScienceDirect("artificial intelligence")
sd.search()
sd.get_results()
sd.get_bibtex()
