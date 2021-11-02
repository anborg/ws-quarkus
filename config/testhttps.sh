#-v
URL1='https://localhost:8043/api/v1/asset/health'
echo $URL1
curl -k  $URL1
echo
URL2='https://localhost:8043/api/v1/asset/health/secure'
echo $URL2
curl -k --netrc-file testhttpspwd.txt  $URL2
echo
URL3='https://localhost:8043/api/v1/asset/workorders/1'
echo $URL3
curl -k --netrc-file testhttpspwd.txt  $URL3
echo
