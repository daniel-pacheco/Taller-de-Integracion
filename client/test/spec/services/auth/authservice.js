'use strict';

describe('Service: auth/AuthService', function () {

  // load the service's module
  beforeEach(module('clientAppApp'));

  // instantiate service
  var auth/AuthService;
  beforeEach(inject(function (_auth/AuthService_) {
    auth/AuthService = _auth/AuthService_;
  }));

  it('should do something', function () {
    expect(!!auth/AuthService).toBe(true);
  });

});
