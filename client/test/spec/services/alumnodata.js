'use strict';

describe('Service: alumnoData', function () {

  // load the service's module
  beforeEach(module('clientAppApp'));

  // instantiate service
  var alumnoData;
  beforeEach(inject(function (_alumnoData_) {
    alumnoData = _alumnoData_;
  }));

  it('should do something', function () {
    expect(!!alumnoData).toBe(true);
  });

});
