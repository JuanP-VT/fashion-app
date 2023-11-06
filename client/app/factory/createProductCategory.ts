import ValidationError from "../utils/error/ValidationError";

/**
 * Creates a new `ProductCategory` object with the specified name and image URL.
 *
 * @remarks
 * This function will create a product category object, ensuring that the name
 * provided meets certain validation criteria. The name must be a non-empty string,
 * and cannot be `null`. If these conditions are not met, the function will throw an error.
 *
 * @param name - The name of the product category. Must be a non-empty string.
 * @param imageUrl - The URL of the image associated with the product category.
 * @returns The newly created `ProductCategory` object.
 *
 * @example
 *
 * const electronicsCategory = createProductCategory("Electronics", "https://example.com/electronics.jpg");
 *
 *
 * @throws Will throw a validation error if `name` is `null`.
 * @throws Will throw a validation error if `name` is not a string.
 * @throws Will throw a validation error if `name` is an empty string.
 * @throws Will throw a validation error if  imageUrl` is null`.
 *
 * @public
 */
export default function createProductCategory(
  name: string,
  imageUrl: string
): ProductCategory {
  if (name === null) {
    throw new ValidationError("Product category name is required");
  }
  if (typeof name !== "string") {
    throw new ValidationError("Product category name must be of type string");
  }
  if (name === "") {
    throw new ValidationError("Product category name cannot be blank");
  }
  if (imageUrl === null){
    throw new ValidationError("ImageUrl is required")
  }
  const newProductCategory = { name, imageUrl };
  return newProductCategory;
}
