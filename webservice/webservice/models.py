from django.db import models

class Subject(models.Model):
	tag_id = models.IntegerField(primary_key=True)

	start_time = models.DateTimeField('start time')
	
	end_time = models.DateTimeField('end time')
	
	name = models.CharField(max_length=200)

	duration = models.IntegerField(default=0)
	
	description = models.TextField()
	
	def __str__(self):
		return "{} {} {}".format(self.tag_id, 
								 self.start_time, 
								 self.end_time)
