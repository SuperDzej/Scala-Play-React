import Authentication from './Authentication'

const UserAuth = {
  storageKey: 'token',
  isAuthenticated() {
    var authorization = this.getAuthorization()
    return authorization !== null
  },
  getAuthorization() {
    var authObj = localStorage.getItem(this.storageKey)
    return authObj ? `${authObj.schema} ${authObj.token}` : null 
  },
  authenticate(username, password, cb) {
    Authentication.getToken(username, password, (response) => {
      localStorage.setItem(this.storageKey, response)
      cb()
    })
  },
  signout(cb) {
    localStorage.removeItem(this.storageKey)
    setTimeout(cb, 100);
  }
}

export default UserAuth
