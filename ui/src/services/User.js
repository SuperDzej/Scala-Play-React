import Client from "./Client";

function getWithOffsetAndLimit(offset, limit, cb) {
  Client.get(`/api/users/${offset}/${limit}`, cb);
}

function post(data, cb) {
  Client.post('/api/users', data, cb)
}

const User = { getWithOffsetAndLimit, post };
export default User;
