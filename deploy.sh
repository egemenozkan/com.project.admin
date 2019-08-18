mvn -f /Devel/workspace/com.project.contract/ clean install
mvn clean package -DskipTests -Pprod
scp target/admin-alfa.jar root@do.sevais.com:/opt/www/admin/admin-alfa.jar