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

DEBUG = False

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

        if DEBUG:
            print tmp_url

        request = urllib2.Request(tmp_url)
        response = urllib2.urlopen(request)
        results_page = PyQuery(response.read())

        self.results_html = results_page

    def get_bibtex_url(self):
        """Try to generate URL to download Bibtex file"""
        form_page = PyQuery(self.results_html)
        form = form_page("form[name='Tag']")

        hidden_searchtype = form("input[name='searchtype']").val()
        hidden_refsource = form("input[name='refSource']").val()
        hidden_st = form("input[name='_st']").val()
        hidden_count = form("input[name='count']").val()
        hidden_sort = form("input[name='sort']").val()
        hidden_filtertype = form("input[name='filtertype']").val()
        hidden_chunk = form("input[name='_chunk']").val()
        hidden_hitcount = form("input[name='hitCount']").val()
        hidden_nextlist = form("input[name='NEXT_LIST']").val()
        hidden_view = form("input[name='view']").val()
        hidden_articlelistid = form("input[name='_ArticleListID']").val()
        hidden_chunksize = form("input[name='chunkSize']").val()
        hidden_sisrsearch = form("input[name='sisr_search']").val()
        hidden_totalpages = form("input[name='TOTAL_PAGES']").val()
        hidden_md5 = form("input[name='md5']").val()

        values = [
            ("_ob", "ArticleListURL"),
            ("_method", "tag"),
            ("searchtype", hidden_searchtype or ""),
            ("refSource", hidden_refsource or ""),
            ("_st", hidden_st or ""),
            ("count", hidden_count or ""),
            ("sort", hidden_sort or ""),
            ("filterType", hidden_filtertype or ""),
            ("_chunk", hidden_chunk or ""),
            ("hitCount", hidden_hitcount or ""),
            ("NEXT_LIST", hidden_nextlist or ""),
            ("view", hidden_view or ""),
            ("md5", hidden_md5 or ""),
            ("_ArticleListID", hidden_articlelistid or ""),
            ("chunkSize", hidden_chunksize or ""),
            ("sisr_search", hidden_sisrsearch or ""),
            ("TOTAL_PAGES", hidden_totalpages or ""),
            ("zone", "exportDropDown"),
            ("citation-type", "BIBTEX"),
            ("format", "cite-abs"),
            ("export", "Export"),
            ("bottomPaginationBoxChanged", ""),
            ("displayPerPageFlag", "f"),
            ("resultsPerPage", "25"),
        ]

        get_parameters = urllib.urlencode(values)

        tmp_url = self.results_url + "?" + get_parameters

        if DEBUG:
            print tmp_url

        self.bibtex_url = tmp_url
        return tmp_url

    def print_bibtex_url(self):
        """Print URL to Bibtex file"""
        print self.bibtex_url
