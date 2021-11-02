keytool -genkey -keyalg RSA -keysize 2048 -alias selfsigned \
  -keystore keystore.jks \
  -storepass password \
  -validity 365

#https://github.com/quarkusio/quarkus-quickstarts/tree/main/grpc-tls-quickstart/src/main/resources/tls

#The ca is self-signed:

openssl req -x509 -new -newkey rsa:2048 -nodes -keyout key.pem -out cert.pem \
  -config ca-openssl.cnf -days 3650 -extensions v3_req

# Client is issued by CA:

openssl genrsa -out client.key.rsa 2048
openssl pkcs8 -topk8 -in client.key.rsa -out client.key -nocrypt
openssl req -new -key client.key -out client.csr

# When prompted for certificate information, everything is default except the common name which is set to testclient.

openssl x509 -req -CA ca.pem -CAkey ca.key -CAcreateserial -in client.csr \
  -out client.pem -days 3650


# server is issued by CA with a special config for subject alternative names:

openssl genrsa -out server1.key.rsa 2048
openssl pkcs8 -topk8 -in server.key.rsa -out server.key -nocrypt
openssl req -new -key server.key -out server.csr -config server-openssl.cnf

# When prompted for certificate information, everything is default except the common name which is set to localhost.

openssl x509 -req -CA ca.pem -CAkey ca.key -CAcreateserial -in server.csr \
  -out server.pem -extfile server-openssl.cnf -days 3650

# Cleanup

rm *.rsa
rm *.csr
rm ca.srl


