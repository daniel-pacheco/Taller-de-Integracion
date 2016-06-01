'use strict';

describe('Service: alumnoService', function () {

  // load the service's module
  beforeEach(module('clientAppApp'));

  // instantiate service
  var alumnoService;
  beforeEach(inject(function (_alumnoService_) {
    alumnoService = _alumnoService_;
  }));

  it('should do something', function () {
    expect(!!alumnoService).toBe(true);
  });

});
