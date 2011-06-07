package org.archstudio.relengtools.core.action;

import static org.archstudio.sysutils.SystemUtils.emptyIfNull;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.archstudio.eclipse.ui.actions.AbstractObjectActionDelegate;
import org.archstudio.relengtools.core.Activator;
import org.archstudio.sysutils.SystemUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.google.common.collect.Lists;

public class SortManifests extends AbstractObjectActionDelegate {

	public SortManifests() {
	}

	@Override
	public void run(IAction action) {
		for (IProject project : getProjects(selection)) {
			run(action, project);
		}
	}

	private void run(IAction action, IProject project) {
		try {
			BundleContext context = Activator.getSingleton().getContext();
			ServiceReference ref = context.getServiceReference(IBundleProjectService.class.getName());
			IBundleProjectService service = (IBundleProjectService) context.getService(ref);
			IBundleProjectDescription description = service.getDescription(project);
			List<IRequiredBundleDescription> requiredBundles = Lists.newArrayList(emptyIfNull(description
					.getRequiredBundles()));

			List<IRequiredBundleDescription> newRequiredBundles = Lists.newArrayList(requiredBundles);
			Collections.sort(newRequiredBundles, new Comparator<IRequiredBundleDescription>() {
				@Override
				public int compare(IRequiredBundleDescription o1, IRequiredBundleDescription o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});

			// the old bundles aren't getting removed, we force this by clearing
			// the corresponding header name
			description.setHeader("Require-Bundle", null);
			description.setRequiredBundles(null);
			description.apply(null);

			// the hack above requires that we start with a fresh bundle,
			// because we cannot unclear the header name
			description = service.getDescription(project);
			description.setRequiredBundles(newRequiredBundles.toArray(new IRequiredBundleDescription[0]));
			description.apply(null);

			// now, manually sort the reset of the manifest file
			IFile manifestFile = project.getFile("META-INF/MANIFEST.MF");
			String manifest = new String(SystemUtils.blt(manifestFile.getContents()));
			manifest = manifest.replaceAll(",\r?\n ", ",");
			String[] lines = manifest.split("\r?\n");
			Arrays.sort(lines);
			manifest = SystemUtils.join("", "\n", "", lines);
			manifest = manifest.replaceAll(",", ",\n ");
			if (!manifest.endsWith("\n")) {
				manifest += "\n";
			}
			manifestFile.setContents(new ByteArrayInputStream(manifest.getBytes()), true, true, null);
			manifestFile.refreshLocal(IResource.DEPTH_ZERO, null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
