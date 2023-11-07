module.exports = {
  testEnvironment: "jest-environment-jsdom",
  moduleDirectories: ["node_modules", "<rootDir>/"],
  moduleNameMapper: {
    // Handle CSS imports (with CSS modules)
    "^.+\\.module\\.(css|sass|scss)$": "identity-obj-proxy",
    // Handle CSS imports (without CSS modules)
    "^.+\\.(css|sass|scss)$": "<rootDir>/__mocks__/styleMock.js",
    // Handle image imports
    "^.+\\.(jpg|jpeg|png|gif|webp|svg)$": "<rootDir>/__mocks__/fileMock.js",
    // Handle module aliases
    "^@/components/(.*)$": "<rootDir>/components/$1",
    "^@/app/utils/error/(.*)$": "<rootDir>/app/utils/error/$1",
    "^@/app/factory/(.*)$": "<rootDir>/app/factory/$1",
  },
  transform: {
    // Use swc/jest for transforming files.
    "^.+\\.(js|jsx|ts|tsx)$": ["@swc/jest"],
  },
  transformIgnorePatterns: ["/node_modules/"],
  setupFilesAfterEnv: ["<rootDir>/jest.setup.js"],
  globals: {
    "ts-jest": {
      tsconfig: "<rootDir>/tsconfig.jest.json",
    },
  },
};
