Feature: Buying of product through the cart

Scenario: To choose item in the catalog, choose their quantity and put to the cart
Given User is on the main page
When User chooses item from catalog and choose it's amount
Then User goes to cart
And User submits the order and goes futher
And User fills in delivery form - chooses the region from given
And User fills in delivery form - chooses shipping company from given
And User fills in delivery form - chooses payment method from given
And User fills in delivery form - writes down contact information
And User submits payment of the order
Then Verification - the order succesfully created