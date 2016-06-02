'use strict';

describe('Service: appConstants', function () {

  // load the service's module
  beforeEach(module('clientAppApp'));

  // instantiate service
  var appConstants;
  beforeEach(inject(function (_appConstants_) {
    appConstants = _appConstants_;
  }));

  it('should do something', function () {
    expect(!!appConstants).toBe(true);
  });

});
