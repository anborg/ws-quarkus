s21 - error
- commented that plugin

OIDC error- not configuring it now
- comment
-  io.quarkus.runtime.configuration.ConfigurationException: 'quarkus.oidc.auth-server-url' property must be configured



ADD REDIS CLIENT
mvn quarkus:add-extension -Dextensions="redis-client"
quarkus.redis.hosts=redis://localhost:6379