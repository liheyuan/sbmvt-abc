# create abc
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"abc":"msg1"}' 'http://localhost:8080/sbmvt-abc/api/database/abc'
# get abc
curl 'http://localhost:8080/sbmvt-abc/api/database/abc/1'
