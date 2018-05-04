import Client from "./Client";

function get() {
  return Client.get(`/api/leaves`);
}

function getById(id) {
  return Client.get(`/api/leaves/${id}`)
}

function post(data) {
  return Client.post('/api/leaves', data)
}

function deleteResource(id) {
  return Client.deleteResource(`/api/leaves/${id}`)
}

const Leave = { get, getById, post , deleteResource};
export default Leave
