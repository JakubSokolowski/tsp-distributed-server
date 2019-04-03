# tsp-distributed-server
### Requirements installation
    sudo apt-get install python3.6
    sudo apt-get install python3-pip
    sudo pip3 install virtualenv 
### Project Setup
    1. Clone project using SSH and cd onto project dir
    2. Create virtual environment - virtualenv -p python3 env 
    2. Activate virtual environment - source env/bin/activate
    3. Install recquired packages - pi3 install -r requirements.txt
       NOTE - update the requirements after adding new packages - pip freeze > requirements.txt
### Basic Server Commands (run from tspserver/tspserver dir)
    Start server - python3 manage.py runserver
    Run migrations - python3 manage.py migrate
### GET example
    http://127.0.0.1:8000/bnb/1/
### POST example
    curl -v -H "Content-Type: application/json" -X PUT -d '{"lower_bound": 420}' http://127.0.0.1:8000/bnb/1/


