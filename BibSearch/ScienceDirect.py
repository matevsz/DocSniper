#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Mariusz Kozakowski 2016

"""Module for searching and getting results from ScienceDirect"""

import urllib
import urllib2
#import cookielib
from pyquery import PyQuery

from Search import Search

class ScienceDirect(Search):
    """Class to handle ScienceDirect source"""
    source = "ScienceDirect"
    search_url = "http://www.sciencedirect.com/science/search"
    results_url = "http://www.sciencedirect.com/science"

    def search(self):
        """Try to search for `keywords` in Computer Science"""
        request = urllib2.Request(self.search_url)
        response = urllib2.urlopen(request)

        form_page = PyQuery(response.read())
        form = form_page("form[name='Form1']")

        # find hidden form values
        hidden_ob = form("input[name='_ob']").val()
        hidden_method = form("input[name='_method']").val()
        hidden_acct = form("input[name='_acct']").val()
        hidden_temp = form("input[name='_temp']").val()
        hidden_md5 = form("input[name='md5']").val()
        hidden_test_alid = form("input[name='test_alid']").val()

        # values for search GET
        # order does matter :O
        values = [
            ("_ob", hidden_ob),
            ("_method", hidden_method or ""),
            ("_acct", hidden_acct or ""),
            ("_temp", hidden_temp or ""),
            ("md5", hidden_md5 or ""),
            ("test_alid", hidden_test_alid or ""),
            ("SearchText", self.keywords),
            ("keywordOpt", "ALL"),
            ("addTerm", "0"),
            ("addSearchText", ""),
            ("addkeywordOpt", "ALL"),
            ("source", "srcJrl"), # Journals
            ("source", "srcBk"), # Books
            ("srcSel", "11"), # 11 is Computer Science
            ("DateOpt", "2"),
            ("fromDate", "2006"),
            ("toDate", "Present"),
            ("RegularSearch", "Search")
        ]
        get_parameters = urllib.urlencode(values)

        tmp_url = self.results_url + "?" + get_parameters
        request = urllib2.Request(tmp_url)
        response = urllib2.urlopen(request)
        results_page = PyQuery(response.read())

        self.results = results_page
