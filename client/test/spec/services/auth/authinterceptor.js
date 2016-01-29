'use strict';

describe('Service: auth/AuthInterceptor', function () {

  // load the service's module
  beforeEach(module('clientAppApp'));

  // instantiate service
  var auth/AuthInterceptor;
  beforeEach(inject(function (_auth/AuthInterceptor_) {
    auth/AuthInterceptor = _auth/AuthInterceptor_;
  }));

  it('should do something', function () {
    expect(!!auth/AuthInterceptor).toBe(true);
  });

});
