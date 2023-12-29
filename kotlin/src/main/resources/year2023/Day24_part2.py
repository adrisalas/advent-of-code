import z3

hail = [[int(i) for i in l.replace('@',',').split(',')]
                for l in open('Day24.txt')]

rock = z3.RealVector('r', 6)
time = z3.RealVector('t', 3)

s = z3.Solver()
s.add(*[rock[d] + rock[d+3] * t == hail[d] + hail[d+3] * t
        for t, hail in zip(time, hail) for d in range(3)])
s.check()

print(s.model().eval(sum(rock[:3])))

#Thanks reddit: https://topaz.github.io/paste/#XQAAAQBfAQAAAAAAAAA0m0pnuFI8dAmllNqCbtidg93/PNdTDHVZ1U9WLrnhBYCFOq34BCEJrDwu0abMlpuyt+Sx8pw5sfrpiEx74s2ptZOmO8ww+tcd9u77lZbsMRKYYdIpJWIqfLifC9RZ8KzYPJTm4hlIgKZWAIksdAVkfHeffbFQVRwL0Vyq/aoy79c/U6+nAXYoc5hbOZ9MADeiwlsRkTDzQNd6gctRy3hJwwxg3XIhu5CCCAzepNgl8/xqd7Nk7QmCCEBlXUskCjC9IPr9dScYOCEv7kau+8l5UjIRIvhTjhSxEy5qM++4RuRPfyZWGCOyBJHOSQvxHf6fsoY=
