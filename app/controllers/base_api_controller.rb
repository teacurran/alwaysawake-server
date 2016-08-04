class BaseApiController < ApplicationController

	def validate_json(condition)
		unless condition
			render nothing: true, status: :bad_request
		end
	end

	def update_values(ivar, attributes)
		instance_variable_get(ivar).assign_attributes(attributes)
		if instance_variable_get(ivar).save
			render nothing: true, status: :ok
		else
			render nothing: true, status: :bad_request
		end
	end

	def check_existence(ivar, object, finder)
		instance_variable_set(ivar, instance_eval(object+"."+finder))
	end

end
