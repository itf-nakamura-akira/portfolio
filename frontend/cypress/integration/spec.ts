describe('My First Test', () => {
    it('Visits the initial project page', () => {
        cy.visit('/');
        cy.contains('Portfolio');
        cy.contains('ダッシュボード');
        cy.contains('ユーザー設定');
    });
});
