export default class ValidationError extends Error {
  constructor(message: string) {
    super(message); // 'Error' breaks prototype chain here
    this.name = "Validation Error";
    Object.setPrototypeOf(this, new.target.prototype); // restore prototype chain
  }
}
