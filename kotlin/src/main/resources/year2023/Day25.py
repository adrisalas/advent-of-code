from copy import deepcopy
from collections import defaultdict, Counter, deque
from math import gcd
import heapq
import math
import networkx as nx
import re
import sys

D = open("Day25.txt").read().strip()
L = D.split('\n')
G = [[c for c in row] for row in L]
R = len(G)
C = len(G[0])


E = defaultdict(set)
for line in L:
  s,e = line.split(':')
  for y in e.split():
    E[s].add(y)
    E[y].add(s)
n = len(E)

G = nx.DiGraph()
for k,vs in E.items():
  for v in vs:
    G.add_edge(k,v,capacity=1.0)
    G.add_edge(v,k,capacity=1.0)

for x in [list(E.keys())[0]]:
  for y in E.keys():
    if x!=y:
      cut_value, (L,R) = nx.minimum_cut(G, x, y)
      if cut_value == 3:
        print(len(L)*len(R))
        break

#Thanks reddit: https://github.com/jonathanpaulson/AdventOfCode/blob/master/2023/25.py