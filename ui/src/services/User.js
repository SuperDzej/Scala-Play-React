import Client from "./Client";

function getWithOffsetAndLimit(offset, limit) {
  return Client.get(`/api/users/${offset}/${limit}`);
}

function getById(id) {
  return Client.get(`/api/users/${id}`)
}

function getTotal() {
  return Client.get('/api/users/total')
}

function getInfo() {
  return Client.get('/api/users/me')
}

function post(data) {
  return Client.post('/api/users', data)
}

function deleteResource(id) {
  return Client.deleteResource(`/api/users/${id}`)
}

const User = { getWithOffsetAndLimit, getById, getTotal, getInfo, post , deleteResource};
export default User
