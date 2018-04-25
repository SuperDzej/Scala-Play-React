import Client from "./Client";

function getWithOffsetAndLimit(offset, limit, cb) {
  Client.get(`/api/users/${offset}/${limit}`, cb);
}

function getTotal(cb) {
  Client.get('/api/users/total', cb)
}

function post(data, cb) {
  Client.post('/api/users', data, cb)
}

const User = { getWithOffsetAndLimit, getTotal, post };
export default User;
