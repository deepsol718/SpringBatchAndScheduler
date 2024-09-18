SELECT id, name
	FROM epen.indian_names;

select * from public.batch_job_execution

select * from public.batch_step_execution_context

select * from epen.table_a

delete from epen.indian_names;
delete from epen.table_a;
delete from public.batch_step_execution_context;
delete from public.batch_step_execution;
delete from public.batch_job_execution_params;
delete from public.batch_job_execution_context;
delete from public.batch_job_execution;
delete from public.batch_job_instance;


delete from t325.batch_step_execution_context;
delete from t325.batch_step_execution;
delete from t325.batch_job_execution_params;
delete from t325.batch_job_execution_context;
delete from t325.batch_job_execution;
delete from t325.batch_job_instance;

drop table epen.batch_step_execution_context,
	epen.batch_step_execution,
	epen.batch_job_execution_params,
	epen.batch_job_execution_context,
	epen.batch_job_execution,
	epen.batch_job_instance;

drop table t325.batch_step_execution_context,
	t325.batch_step_execution,
	t325.batch_job_execution_params,
	t325.batch_job_execution_context,
	t325.batch_job_execution,
	t325.batch_job_instance;

drop table public.batch_step_execution_context,
	public.batch_step_execution,
	public.batch_job_execution_params,
	public.batch_job_execution_context,
	public.batch_job_execution,
	public.batch_job_instance;

