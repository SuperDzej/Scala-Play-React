import Authentication from './Authentication'

const UserAuth = {
  storageKey: 'token',
  authentication: Authentication,
  isAuthenticated() {
    var authorization = this.getAuthorization()
    return authorization !== null
  },
  getAuthorization() {
    var authObj = localStorage.getItem(this.storageKey)
    return authObj ? `${authObj.schema} ${authObj.token}` : null 
  },
  authenticate(username, password, cb) {
    this.authentication.getToken(username, password)
      .then((response) => {
        localStorage.setItem(this.storageKey, response)
        cb(null, response)
      })
      .catch((e) => {
        cb(e, null)
      })
  },
  signout(cb) {
    localStorage.removeItem(this.storageKey)
    setTimeout(cb, 100);
  }
}

export default UserAuth
