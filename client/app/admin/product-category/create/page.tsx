"use client";
import { useState, FormEvent, useEffect } from "react";
import { Button, Input, Chip } from "@nextui-org/react";
import createProductCategory from "@/app/factory/createProductCategory";
import ValidationError from "@/app/utils/error/ValidationError";
import { color } from "@/app/types/color";
import React from "react";
/**
 * Component to register a new ProductCategory in the database.
 * It provides a form for input and visual feedback for the operation's outcome.
 */
function CreateProductCategory() {
  // State for storing form inputs.
  const [form, setForm] = useState<ProductCategory>({ name: "", imageUrl: "" });

  // State to provide user feedback from API calls.
  const [apiFeedback, setApiFeedback] = useState<string>("");

  // State to control the input fields' outline color based on validation status.

  const [nameInputColor, setNameInputColor] = useState<color>("default");
  const [imageInputColor, setImageInputColor] = useState<color>("default");

  // Resets the form upon successful API response.
  useEffect(() => {
    if (apiFeedback === "Operation was successful") {
      const timeoutID = setTimeout(() => {
        setForm({ name: "", imageUrl: "" });
        setNameInputColor("default");
        setImageInputColor("default");
        setApiFeedback("");
      }, 2000);
      return () => {
        if (timeoutID) clearTimeout(timeoutID);
      };
    }
  }, [apiFeedback]);
  /**
   * Submits the product category form data to the server.
   * It creates a ProductCategory object and handles the API request.
   * Sets feedback for the user based on the API response or any validation errors encountered.
   *
   * @param {FormEvent} event - The form submission event.
   */
  async function handleSubmitProductCategory(event: FormEvent) {
    event.preventDefault();

    // Clear previous feedback and reset input colors.
    setNameInputColor("default");
    setImageInputColor("default");
    setApiFeedback("");
    // Attempt to construct a ProductCategory object.
    try {
      const create = createProductCategory(form.name, form.imageUrl);
      const request = await fetch(
        process.env.NEXT_PUBLIC_PRODUCT_CATEGORY_API,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },

          body: JSON.stringify(create),
        }
      );

      // Provide user feedback based on the HTTP response status.
      if (request.status === 400) {
        setApiFeedback("Invalid Request");
      }

      if (request.status === 409) {
        setApiFeedback("Name already exist in database");
      }
      if (request.status === 201) {
        setApiFeedback("Operation was successful");
      }
    } catch (error) {
      if (error instanceof ValidationError) {
        // Provide user feedback based on specific validation errors.
        const errorMessage = error.message;
        setApiFeedback(error.message);

        if (errorMessage.includes("Name")) {
          setNameInputColor("danger");
          return;
        } else if (errorMessage.includes("ImageUrl")) {
          setImageInputColor("danger");
          return;
        }
      }
    }
  }
  return (
    <div className="flex w-full flex-col justify-center items-center">
      <form
        onSubmit={(event) => handleSubmitProductCategory(event)}
        className="flex flex-col sm:w-1/2 py-10"
      >
        <h1 className="text-center py-5 ">Create Product Category</h1>
        <Input
          color={nameInputColor}
          type="text"
          label="Name"
          value={form.name}
          onChange={(e) =>
            setForm((prevState) => {
              return { ...prevState, name: e.target.value };
            })
          }
        />
        <Input
          value={form.imageUrl}
          color={imageInputColor}
          className="my-3"
          type="text"
          label="ImageUrl"
          onChange={(e) =>
            setForm((prevState) => {
              return { ...prevState, imageUrl: e.target.value };
            })
          }
        />
        <Button type="submit" className="w-32 my-2">
          Submit
        </Button>
        {apiFeedback === "" ? "" : <Chip variant="dot">{apiFeedback}</Chip>}
      </form>
    </div>
  );
}

export default CreateProductCategory;
