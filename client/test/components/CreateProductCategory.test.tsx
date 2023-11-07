import {
  fireEvent,
  render,
  waitFor,
  screen,
  act,
} from "@testing-library/react";
import "@testing-library/jest-dom";
import React from "react";
import fetchMock from "jest-fetch-mock";
import CreateProductCategory from "../../app/admin/product-category/create/page";

fetchMock.enableMocks();
// Set up environment variable
process.env.NEXT_PUBLIC_PRODUCT_CATEGORY_API = "your-api-endpoint";
beforeEach(() => {
  fetchMock.resetMocks();
});

// Test component renders correctly
it("renders correctly", () => {
  render(<CreateProductCategory />);
  expect(screen.getByText("Create Product Category")).toBeInTheDocument();
});

// Test input for product name updates on change
it("updates product name on change", () => {
  render(<CreateProductCategory />);
  const nameInput = screen.getByLabelText("Name") as HTMLInputElement;
  fireEvent.change(nameInput, { target: { value: "New Category" } });
  expect(nameInput.value).toBe("New Category");
});

// Test input for image URL updates on change
it("updates image URL on change", () => {
  render(<CreateProductCategory />);
  const imageUrlInput = screen.getByLabelText("ImageUrl") as HTMLInputElement;
  fireEvent.change(imageUrlInput, {
    target: { value: "http://example.com/image.png" },
  });
  expect(imageUrlInput.value).toBe("http://example.com/image.png");
});

it("shows success message after successful form submission", async () => {
  // Set the response for the fetch call
  fetchMock.mockResponseOnce(JSON.stringify({}), { status: 201 });

  // Render the component
  render(<CreateProductCategory />);

  // Get the input fields and submit button
  const nameInput = screen.getByLabelText("Name");
  const imageUrlInput = screen.getByLabelText("ImageUrl");
  const submitButton = screen.getByText("Submit");

  // Change the input fields
  fireEvent.change(nameInput, { target: { value: "Electronics" } });
  fireEvent.change(imageUrlInput, {
    target: { value: "http://example.com/electronics.png" },
  });

  // Click on the submit button
  fireEvent.click(submitButton);

  // Wait for the success message
  await waitFor(() => {
    expect(screen.getByText("Operation was successful")).toBeInTheDocument();
  });
});

// 5. Test API feedback shows error when name already exists
it("shows error when name already exists", async () => {
  fetchMock.mockResponseOnce("", { status: 409 });
  render(<CreateProductCategory />);
  // Get the input fields and submit button
  const nameInput = screen.getByLabelText("Name");
  const imageUrlInput = screen.getByLabelText("ImageUrl");

  // Change the input fields
  fireEvent.change(nameInput, { target: { value: "Electronics" } });
  fireEvent.change(imageUrlInput, {
    target: { value: "http://example.com/electronics.png" },
  });
  fireEvent.click(screen.getByText("Submit"));
  await waitFor(() => {
    expect(
      screen.getByText("Name already exist in database")
    ).toBeInTheDocument();
  });
});

// 6. Test API feedback shows error on invalid request
it("shows error on invalid request", async () => {
  fetchMock.mockResponseOnce("", { status: 400 });
  render(<CreateProductCategory />);
  // Get the input fields and submit button
  const nameInput = screen.getByLabelText("Name");
  const imageUrlInput = screen.getByLabelText("ImageUrl");

  // Change the input fields
  fireEvent.change(nameInput, { target: { value: "Electronics" } });
  fireEvent.change(imageUrlInput, {
    target: { value: "http://example.com/electronics.png" },
  });
  fireEvent.click(screen.getByText("Submit"));
  await waitFor(() => {
    expect(screen.getByText("Invalid Request")).toBeInTheDocument();
  });
});

// Test form state resets after successful submission
it("resets form state after successful submission", async () => {
  jest.useFakeTimers();
  fetchMock.mockResponseOnce(JSON.stringify({}), { status: 201 });
  render(<CreateProductCategory />);
  // Trigger event
  fireEvent.change(screen.getByLabelText("Name"), {
    target: { value: "New Category" },
  });
  fireEvent.change(screen.getByLabelText("ImageUrl"), {
    target: { value: "http://example.com/image.png" },
  });
  fireEvent.click(screen.getByText("Submit"));
  await waitFor(() => {
    expect(screen.getByText("Operation was successful")).toBeInTheDocument();
  });
  await act(async () => {
    jest.runAllTimers(); // Advance all timers inside act
  });
  const nameInput = screen.getByLabelText("Name") as HTMLInputElement;
  expect(nameInput.value).toBe("");
  const imageInput = screen.getByLabelText("ImageUrl") as HTMLInputElement;
  expect(imageInput.value).toBe("");
  jest.useRealTimers();
});
