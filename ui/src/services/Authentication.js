import Client from "./Client";

function getToken(email, password, cb) {
  var data = {
    email: email,
    password: password
  }
  
  Client.post('/token', data, cb);
}

const Authentication = { 
  getToken
};

export default Authentication;
