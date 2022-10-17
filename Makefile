CLASSPATH :=../po-uilib/po-uilib.jar:./prr-app/prr-app.jar:./prr-core/prr-core.jar

.PHONY = all clean install run test tests

all:
	(cd prr-core; make $(MFLAGS) all)
	(cd prr-app; make $(MFLAGS) all)

clean:
	(cd prr-core; make $(MFLAGS) clean)
	(cd prr-app; make $(MFLAGS) clean)
	(cd tests; rm -rf *.diff *.outhyp)
	rm *.dat

install:
	(cd prr-core; make $(MFLAGS) install)
	(cd prr-app; make $(MFLAGS) install)

run:
	java -cp $(CLASSPATH) prr.app.App

test: 
	@./runtests.sh $(CLASSPATH) tests

