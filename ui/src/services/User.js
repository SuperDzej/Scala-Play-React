import Client from "./Client";

function getWithOffsetAndLimit(offset, limit, ) {
  return Client.get(`/api/users/${offset}/${limit}`);
}

function getTotal() {
  return Client.get('/api/users/total')
}

function post(data) {
  return Client.post('/api/users', data)
}

const User = { getWithOffsetAndLimit, getTotal, post };
export default User;
