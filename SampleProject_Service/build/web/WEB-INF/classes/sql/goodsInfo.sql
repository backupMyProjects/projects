SELECT goods. * , users.name as users_name, groups.name as groups_name
FROM goods, users, groups
WHERE goods.current_winner = users.id
AND users.groups = groups.id

# CREATE VIEW `bidbuy`.`goodsInfo` AS 