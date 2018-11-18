# to install --> pip install newsapi-python

from newsapi import NewsApiClient
import json

# Init
newsapi = NewsApiClient(api_key='4f4e19ab063e47538bacd4ee122f20cf')

# /v2/top-headlines
# top_headlines = newsapi.get_top_headlines(q='Mark Zuckerberg',
#                                           category='technology',
#                                           language='en')
# print(json.dumps(top_headlines, indent=1))

# # /v2/everything
all_articles = newsapi.get_everything(
  q='Mark Zuckerberg',
  sources='bbc-news',
  language='en')
print(json.dumps(all_articles, indent=1))

# # /v2/sources
# sources = newsapi.get_sources()

# print(sources)