#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Mariusz Kozakowski 2016

"""Module for searching and getting results from directories"""

#import urllib
import urllib2
import cookielib

class Search(object):
    """Generic search class"""
    user_agent = "DocSniper - BibSearch Module"
    source = "None"

    def __init__(self, keywords):
        self.keywords = keywords

        self.results = None
        self.results_html = None
        self.bibtex_url = None
        self.cookie = cookielib.CookieJar()
        self.opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(self.cookie))
        self.opener.addheaders = [{'User-agent', self.user_agent}]
        urllib2.install_opener(self.opener)

    def search(self):
        """This method should implement search related things"""
        print "Not implemented for source %s ;(" % (self.source)

    def get_results(self):
        """Method downloading results of search"""
        print "Not implemented for source %s ;(" % (self.source)

    def get_bibtex(self):
        """Method downloading a bibtex file with results"""
        print "Not implemented or supported for source %s ;(" % (self.source)
