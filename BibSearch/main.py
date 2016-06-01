#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Mariusz Kozakowski 2016

"""Main program"""

import argparse
from ScienceDirect import ScienceDirect

FLAGS = argparse.ArgumentParser()
FLAGS.add_argument("-q", "--query", required=True, help="search string")
FLAGS.add_argument("-b", "--bibtex", action="store_true", help="return bibtex url")
FLAGS = FLAGS.parse_args()
ARGS = FLAGS

if ARGS.query and len(ARGS.query) > 3:
    SEARCH_ENGINE = ScienceDirect(ARGS.query)
else:
    print "Wrong query! Search query should have at least 3 characters"
    exit(1)

SEARCH_ENGINE.search()
SEARCH_ENGINE.get_bibtex_url()

if ARGS.bibtex:
    SEARCH_ENGINE.print_bibtex_url()
