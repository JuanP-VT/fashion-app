import ValidationError from "../../app/utils/error/ValidationError";
import createProductCategory from "../../app/factory/createProductCategory";

describe("Creates object correctly", () => {
  it("returns a valid object on valid input", () => {
    //Given
    const name = "cloth";
    const imageUrl = "https://e.com";
    const mockCategory: ProductCategory = {
      name,
      imageUrl,
    };
    //When
    const result = createProductCategory(name, imageUrl);
    //Then
    expect(result.name).toBe(mockCategory.name);
    expect(result.imageUrl).toBe(mockCategory.imageUrl);
  });
});

describe("Factory should validate inputs", () => {
  it("should throw error on null name", () => {
    //Given
    const name: any = null;
    const imageUrl = "https://e.com";
    //When
    //Then
    expect(() => {
      const result = createProductCategory(name, imageUrl);
    }).toThrow();
  });

  it("should throw  a validation error on null name", () => {
    //Given
    const name: any = null;
    const imageUrl = "https://e.com";
    //When
    try {
      const result = createProductCategory(name, imageUrl);
    } catch (e) {
      expect(e).toBeInstanceOf(ValidationError);
    }
  });

  it("should throw  a validation error on empty name", () => {
    //Given
    const name = "";
    const imageUrl = "https://e.com";
    //When
    try {
      const result = createProductCategory(name, imageUrl);
    } catch (e) {
      expect(e).toBeInstanceOf(ValidationError);
    }
  });

  it("should throw  a validation error on non string name", () => {
    //Given
    const name: any = 2;
    const imageUrl = "https://e.com";
    //When
    try {
      expect(createProductCategory(name, imageUrl)).toThrow();
      const result = createProductCategory(name, imageUrl);
      //Then
    } catch (e) {
      expect(e).toBeInstanceOf(ValidationError);
    }
  });

  it("should throw  a validation error on non string name", () => {
    //Given
    const name: any = true;
    const imageUrl = "https://e.com";
    //When
    try {
      expect(createProductCategory(name, imageUrl)).toThrow();
      const result = createProductCategory(name, imageUrl);
      //Then
    } catch (e) {
      expect(e).toBeInstanceOf(ValidationError);
    }
  });

  it("should throw  a validation error on non string name", () => {
    //Given
    const name: any = false;
    const imageUrl = "https://e.com";
    //When

    try {
      expect(createProductCategory(name, imageUrl)).toThrow();
      const result = createProductCategory(name, imageUrl);
      //Then
    } catch (e) {
      expect(e).toBeInstanceOf(ValidationError);
    }
  });

  it("should throw  a validation error on null imageUrl", () => {
    //Given
    const name = "cloth";
    const imageUrl: any = null;

    //When
    try {
      expect(createProductCategory(name, imageUrl)).toThrow();
      const result = createProductCategory(name, imageUrl);
      //Then
    } catch (e) {
      expect(e).toBeInstanceOf(ValidationError);
    }
  });

  it("should throw  a validation error on empty imageUrl", () => {
    //Given
    const name = "cloth";
    const imageUrl = "";

    //When
    try {
      expect(createProductCategory(name, imageUrl)).toThrow();
      const result = createProductCategory(name, imageUrl);
      //Then
    } catch (e) {
      expect(e).toBeInstanceOf(ValidationError);
    }
  });
});
