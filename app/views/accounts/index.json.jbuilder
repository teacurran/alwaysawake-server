json.array!(@accounts) do |account|
  json.extract! account, :id, :name, :password
  json.url account_url(account, format: :json)
end
