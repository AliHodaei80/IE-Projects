import React from 'react';
import '../styles/AuthPage.css'; 
const AuthPage = () => {
  return (
    <div className="container my-5">
      <div className="row">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title">Login</h2>
              <form>
                <div className="mb-3">
                  <label htmlFor="login-username" className="form-label">Username</label>
                  <input type="text" className="form-control" id="login-username" placeholder="Enter username"/>
                </div>
                <div className="mb-3">
                  <label htmlFor="login-password" className="form-label">Password</label>
                  <input type="password" className="form-control" id="login-password" placeholder="Password"/>
                </div>
                <button type="submit" className="btn btn-primary w-100">Login</button>
              </form>
            </div>
          </div>
        </div>
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title">Register</h2>
              <form>
                <div className="mb-3">
                  <label htmlFor="register-username" className="form-label">Username</label>
                  <input type="text" className="form-control" id="register-username" placeholder="Enter username"/>
                </div>
                <div className="mb-3">
                  <label htmlFor="register-password" className="form-label">Password</label>
                  <input type="password" className="form-control" id="register-password" placeholder="Password"/>
                </div>
                <button type="submit" className="btn btn-danger w-100">Register</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuthPage;
