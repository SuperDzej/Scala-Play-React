/* eslint-disable no-undef */

function coreRequest(route, headers, method, payload, cb) {
  var data = payload ? JSON.stringify(payload) : null;
  return fetch(route, {
      headers: new Headers({
        // 'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVtYWlsQGdtYWlsLmNvbSIsInVzZXJJZCI6OCwicm9sZSI6IkN1c3RvbWVyIn0.ChLWez-4-APQA9fhdV4kz3wNGXNhSxlxoiHE4AJPk-E', 
        ...headers
      }),
      method: method,
      body: data
    })
    .then(checkStatus)
    .then(parseJSON)
    .then(cb);
}

function getCoreHeaders() {
 return {
    accept: "application/json",
    "content-type": "application/json"
  };
}

function get(route, cb) {
  coreRequest(route, getCoreHeaders(), "GET", null, cb);
}

function post(route, payload, cb) {
  coreRequest(route, getCoreHeaders(), "POST", payload, cb);
}

function put(route, payload, cb) {
  coreRequest(route, getCoreHeaders(), "PUT", payload, cb);
}

function deleteResource(route, cb) {
  coreRequest(route, getCoreHeaders(), "DELETE", null, cb);
}

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }

  const error = new Error(`HTTP Error ${response.statusText}`);
  error.status = response.statusText;
  error.response = response;
  console.log(error); // eslint-disable-line no-console
  throw error;
}

function parseJSON(response) {
  return response.json();
}

const Client = { get , post, put, deleteResource };
export default Client;
