// jest.config.js
module.exports = {
    preset: 'jest-preset-angular',
    setupFilesAfterEnv: ['<rootDir>/src/setup-jest.ts'],
    globalSetup: 'jest-preset-angular/global-setup',
    testMatch: ['<rootDir>/src/**/+(*.)+(spec).+(ts)'],
    moduleNameMapper: {
        '^src/(.*)$': '<rootDir>/src/$1',
        '^app/(.*)$': '<rootDir>/src/app/$1',
        '^assets/(.*)$': '<rootDir>/src/assets/$1',
        '^environments/(.*)$': '<rootDir>/src/environments/$1',
    },
};
