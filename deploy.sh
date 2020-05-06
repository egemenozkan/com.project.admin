mvn -f /Devel/workspace/com.project.contract/ clean install
mvn clean package -DskipTests -Pprod
scp target/admin-alfa.jar root@94.237.94.131:/var/www/jee_projects/admin